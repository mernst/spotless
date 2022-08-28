/*
 * Copyright 2022 DiffPlug
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
package com.diffplug.spotless.java;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.diffplug.spotless.FormatterStep;
import com.diffplug.spotless.ResourceHarness;
import com.diffplug.spotless.SerializableEqualityTester;

class FormatAnnotationsStepTest extends ResourceHarness {
	@Test
	void formatAnnotations() throws Throwable {
		FormatterStep step = FormatAnnotationsStep.create();
		assertOnResources(step, "java/formatannotations/FormatAnnotationsTestInput.test", "java/formatannotations/FormatAnnotationsTestOutput.test");
	}

	@Test
	void formatAnnotationsInComments() throws Throwable {
		FormatterStep step = FormatAnnotationsStep.create();
		assertOnResources(step, "java/formatannotations/FormatAnnotationsInCommentsInput.test", "java/formatannotations/FormatAnnotationsInCommentsOutput.test");
	}

	@Test
	void formatAnnotationsAddRemove() throws Throwable {
		FormatterStep step = FormatAnnotationsStep.create(Arrays.asList("Empty", "NonEmpty"), Arrays.asList("Localized"));
		assertOnResources(step, "java/formatannotations/FormatAnnotationsAddRemoveInput.test", "java/formatannotations/FormatAnnotationsAddRemoveOutput.test");
	}

	@Test
	void doesntThrowIfFormatAnnotationsIsntSerializable() {
		FormatAnnotationsStep.create();
	}

	@Test
	void equality() throws Exception {
		new SerializableEqualityTester() {
			@Override
			protected void setupTest(API api) {
				api.areDifferentThan();
			}

			@Override
			protected FormatterStep create() {
				return FormatAnnotationsStep.create();
			}
		}.testEquals();
	}

}
