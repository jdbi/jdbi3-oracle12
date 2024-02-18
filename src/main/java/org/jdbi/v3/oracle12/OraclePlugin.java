/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jdbi.v3.oracle12;

import java.sql.Types;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.argument.Arguments;
import org.jdbi.v3.core.argument.NullArgument;
import org.jdbi.v3.core.spi.JdbiPlugin;

/**
 * Handles the specific Oracle feature, which does not allow {@link Types#OTHER} for untyped nulls,
 * but it does allow {@link Types#NULL}.
 */
public class OraclePlugin implements JdbiPlugin {
    @Override
    public void customizeJdbi(Jdbi jdbi) {
        jdbi.getConfig(Arguments.class).setUntypedNullArgument(new NullArgument(Types.NULL));
    }
}
