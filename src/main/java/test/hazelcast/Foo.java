package test.hazelcast;

import java.io.Serializable;
import java.time.Instant;

public class Foo implements Serializable {
	
	private static final long serialVersionUID = -4558347535267494374L;
	
	private final Instant updated;
	private final Integer id;
	
	public Foo(Integer id) {
		this(id, null);
	}
	
	public Foo(Integer id, Instant updated) {
		super();
		this.updated = updated;
		this.id = id;
	}

	public Instant getUpdated() {
		return updated;
	}
	
	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Foo [updated=" + updated + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((updated == null) ? 0 : updated.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Foo other = (Foo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (updated == null) {
			if (other.updated != null)
				return false;
		} else if (!updated.equals(other.updated))
			return false;
		return true;
	}
	
	
}
