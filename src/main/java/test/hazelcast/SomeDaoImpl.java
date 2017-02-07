package test.hazelcast;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@CacheConfig(cacheNames="foo")
public class SomeDaoImpl implements SomeDao {
	
	private final ConcurrentMap<Integer, Foo> store = new ConcurrentHashMap<>();
	private final AtomicInteger idgen = new AtomicInteger(0);

	@Override
	@Cacheable(unless="#result == null")
	public Foo load(int id) {
		return store.get(id);
	}

	@Override
	@Cacheable(key="#result.id")
	public Foo save(Foo foo) {
		Integer id = Optional.ofNullable(foo.getId()).orElseGet(() -> idgen.incrementAndGet());
		Foo retval = new Foo(id, Instant.now());
		
		retval = store.putIfAbsent(id, retval);
		
		return retval;
	}

	@Override
	@CacheEvict
	public void delete(int id) {
		store.remove(Integer.valueOf(id));
	}

}
