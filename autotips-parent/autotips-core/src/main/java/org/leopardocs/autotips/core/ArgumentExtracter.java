package org.leopardocs.autotips.core;

public abstract interface ArgumentExtracter {
	public abstract <E> E get(String paramString, Class<E> paramClass);
}