#! /bin/sh

[ 1 -eq ${#} ] && PATH=${PATH}:${1};

PACKAGE="org.gdms.sql.parser";
### ODIR="src/org/gdms/sql/parser";
ODIR="../src/main/java/org/gdms/sql/parser";

jjtree -NODE_PACKAGE:${PACKAGE} -OUTPUT_DIRECTORY:${ODIR} -VISITOR:true -NODE_SCOPE_HOOK:true -MULTI:true sql.jj
javacc -OUTPUT_DIRECTORY:${ODIR} -IGNORE_CASE:true ${ODIR}/sql.jj.jj

cat <<EOF > ${ODIR}/TokenSupport.java
package ${PACKAGE};

public class TokenSupport {
        public Token first_token;

        public Token last_token;
}
EOF

sed -i 's/class SimpleNode implements/class SimpleNode extends TokenSupport implements/' ${ODIR}/SimpleNode.java

jjdoc sql.jj