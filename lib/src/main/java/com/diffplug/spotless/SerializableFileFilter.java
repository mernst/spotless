/*
 * Copyright 2016 DiffPlug
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.diffplug.spotless;

import java.io.FileFilter;
import java.io.Serializable;
import java.util.Arrays;

/** A file filter with full support for serialization. */
public interface SerializableFileFilter extends FileFilter, Serializable {
	/** Creates a FileFilter which will accept all files except files with the given name. */
	public static SerializableFileFilter skipFilesNamed(String name) {
		return new SerializableFileFilterImpl.SkipFilesNamed(name);
	}

	/**
	 * Returns a byte array representation of everything inside this `SerializableFileFilter`.
	 *
	 * The main purpose of this method is to ensure one can't instantiate this class with lambda
	 * expressions, which are notoriously difficult to serialize and deserialize properly. (See
	 * `SerializableFileFilterImpl.SkipFilesNamed` for an example of how to make a serializable
	 * subclass.)
	 */
	public byte[] toBytes();

	/** An implementation of SerializableFileFilter in which equality is based on the serialized representation. */
	public static abstract class EqualityBasedOnSerialization implements SerializableFileFilter {
		private static final long serialVersionUID = 1733798699224768949L;

		@Override
		public byte[] toBytes() {
			return LazyForwardingEquality.toBytes(this);
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(toBytes());
		}

		@Override
		public boolean equals(Object otherObj) {
			if (otherObj == null) {
				return false;
			} else if (otherObj.getClass().equals(this.getClass())) {
				EqualityBasedOnSerialization other = (EqualityBasedOnSerialization) otherObj;
				return Arrays.equals(toBytes(), other.toBytes());
			} else {
				return false;
			}
		}
	}
}
