package test.hazelcast;

import java.time.Instant;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foo")
public class FooController {

	private final SomeDao dao;
	
	@Autowired
	public FooController(SomeDao dao) {
		this.dao = dao;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Foo id(@PathVariable int id, HttpServletResponse resp) {
		Foo retval = dao.load(id);
		if (null == retval) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		return retval;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.POST)
	public String upsert(@PathVariable int id) {
		Instant now = Instant.now();
		//Foo existing = dao.load(id);
		
		Foo newFoo = dao.save(new Foo(id));
		if (newFoo.getUpdated().compareTo(now) >= 0) {
			return "created";
		}
		return "updated";
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public String delete(@PathVariable int id) {
		dao.delete(id);
		return "bahleted!";
	}
}
