/* Generated By:JJTree: Do not edit this line. ASTThrowStatement.java Version 4.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY= */
package org.orbisgis.javaManager.parser;

public class ASTThrowStatement extends SimpleNode {
  public ASTThrowStatement(int id) {
    super(id);
  }

  public ASTThrowStatement(JavaParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JavaParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=7d0fbf2b9a8f80c613c72e94456c75e0 (do not edit this line) */
