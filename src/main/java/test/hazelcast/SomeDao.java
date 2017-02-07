package test.hazelcast;

public interface SomeDao {
	
	public Foo load(int id);
	
	public Foo save(Foo foo);

	public void delete(int id);
}
