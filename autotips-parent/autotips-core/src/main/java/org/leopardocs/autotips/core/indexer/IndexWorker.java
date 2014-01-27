package org.leopardocs.autotips.core.indexer;

import org.apache.lucene.index.IndexWriter;
import org.leopardocs.autotips.core.config.Autotip;

public abstract interface IndexWorker {
	public abstract void doIndex(IndexWriter paramIndexWriter, Autotip paramAutotip) throws Exception;
}