/* Generated By:JJTree: Do not edit this line. ASTFormalParameters.java Version 4.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY= */
package org.orbisgis.javaManager.parser;

public class ASTFormalParameters extends SimpleNode {
  public ASTFormalParameters(int id) {
    super(id);
  }

  public ASTFormalParameters(JavaParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JavaParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=8c43caac36c8c19a2efcfe16970fe33b (do not edit this line) */
