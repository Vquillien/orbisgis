/* Generated By:JJTree: Do not edit this line. ASTExclusiveOrExpression.java Version 4.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY= */
package org.orbisgis.javaManager.parser;

public class ASTExclusiveOrExpression extends SimpleNode {
  public ASTExclusiveOrExpression(int id) {
    super(id);
  }

  public ASTExclusiveOrExpression(JavaParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JavaParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=d75eeb8296fbdaec90ce7fdd9f7482f8 (do not edit this line) */
