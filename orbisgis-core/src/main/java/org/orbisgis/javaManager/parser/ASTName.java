/* Generated By:JJTree: Do not edit this line. ASTName.java Version 4.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY= */
package org.orbisgis.javaManager.parser;

public class ASTName extends SimpleNode {
  public ASTName(int id) {
    super(id);
  }

  public ASTName(JavaParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JavaParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=5db9a2eba2d564f712b2cffd53b7c019 (do not edit this line) */
