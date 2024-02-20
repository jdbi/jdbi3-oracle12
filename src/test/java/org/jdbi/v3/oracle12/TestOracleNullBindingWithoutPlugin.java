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

import java.sql.SQLException;
import java.util.function.Function;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Something;
import org.jdbi.v3.core.statement.SqlStatement;
import org.jdbi.v3.core.statement.UnableToCreateStatementException;
import org.jdbi.v3.core.statement.Update;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.jdbi.v3.testing.junit5.JdbiExtension;
import org.jdbi.v3.testing.junit5.tc.JdbiTestcontainersExtension;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * This test uses an oracle instance in a testcontainer.
 */
@Tag("slow")
@Testcontainers
@EnabledOnOs(architectures = {"x86_64", "amd64"})
public class TestOracleNullBindingWithoutPlugin {

    static final String CONTAINER_VERSION = "gvenzl/oracle-xe:" + System.getProperty("oracle.container.version", "slim-faststart");

    @Container
    static OracleContainer oc = new OracleContainer(CONTAINER_VERSION);

    @RegisterExtension
    public JdbiExtension oracleExtension = JdbiTestcontainersExtension.instance(oc)
        .withInitializer((ds, h) -> {
            h.execute("create table something (id int, name varchar(200))");
        }).withPlugin(new SqlObjectPlugin());

    @Test
    public void testBindNull() {
        Handle h = oracleExtension.getSharedHandle();

        String name = null;
        h.createUpdate("insert into something (id, name) values (:id, :name)")
            .bind("id", 17)
            .bind("name", name)
            .execute();

        assertThat(h.select("select * from something")
            .mapToBean(Something.class)
            .one())
            .isEqualTo(new Something(17, null));
    }

    @Test
    public void testBindNullUntypedThrowsException() {
        Handle h = oracleExtension.getSharedHandle();

        Something something = new Something(17, null);
        Update update = h.createUpdate("insert into something (id, name) values (:id, :name)");
        bindDataWith(update, something, "id", Something::getId);
        bindDataWith(update, something, "name", Something::getName);

        assertThatThrownBy(() -> {
            update.execute();
        }).isInstanceOf(UnableToCreateStatementException.class)
            .hasRootCauseExactlyInstanceOf(SQLException.class)
            .hasRootCauseMessage("ORA-17004: Invalid column type: 1111\nhttps://docs.oracle.com/error-help/db/ora-17004/");

    }

    static <T> SqlStatement<?> bindDataWith(SqlStatement<?> stmt, Something value, String paramName, Function<Something, ?> paramFn) {
        return stmt.bind(paramName, paramFn.apply(value));
    }
}
