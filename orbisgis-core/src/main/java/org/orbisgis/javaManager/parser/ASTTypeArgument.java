/* Generated By:JJTree: Do not edit this line. ASTTypeArgument.java Version 4.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY= */
package org.orbisgis.javaManager.parser;

public class ASTTypeArgument extends SimpleNode {
  public ASTTypeArgument(int id) {
    super(id);
  }

  public ASTTypeArgument(JavaParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JavaParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=3c267a8bb3fb8a0b9cd12efdbb94d198 (do not edit this line) */
