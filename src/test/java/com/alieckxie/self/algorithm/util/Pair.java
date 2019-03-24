package com.alieckxie.self.algorithm.util;

public class Pair<X, Y> {

	X x;
	Y y;

	/**
	 * @param x
	 * @param y
	 */
	public Pair(X x, Y y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the x
	 */
	public X getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public Y getY() {
		return y;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((y == null) ? 0 : y.hashCode());
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		Pair<X, Y> other = (Pair<X, Y>) obj;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		return true;
	}

}