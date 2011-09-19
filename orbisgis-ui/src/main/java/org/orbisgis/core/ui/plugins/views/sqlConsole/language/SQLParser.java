/*
 * OrbisGIS is a GIS application dedicated to scientific spatial simulation.
 * This cross-platform GIS is developed at French IRSTV institute and is able to
 * manipulate and create vector and raster spatial information. OrbisGIS is
 * distributed under GPL 3 license. It is produced by the "Atelier SIG" team of
 * the IRSTV Institute <http://www.irstv.cnrs.fr/> CNRS FR 2488.
 * 
 * 
 * Team leader : Erwan BOCHER, scientific researcher,
 * 
 * User support leader : Gwendall Petit, geomatic engineer.
 * 
 * Previous computer developer : Pierre-Yves FADET, computer engineer, Thomas LEDUC, 
 * scientific researcher, Fernando GONZALEZ CORTES, computer engineer.
 * 
 * Copyright (C) 2007 Erwan BOCHER, Fernando GONZALEZ CORTES, Thomas LEDUC
 * 
 * Copyright (C) 2010 Erwan BOCHER, Alexis GUEGANNO, Maxence LAURENT, Antoine GOURLAY
 * 
 * This file is part of OrbisGIS.
 * 
 * OrbisGIS is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * OrbisGIS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * OrbisGIS. If not, see <http://www.gnu.org/licenses/>.
 * 
 * For more information, please consult: <http://www.orbisgis.org/>
 * 
 * or contact directly:
 * info@orbisgis.org
 */
package org.orbisgis.core.ui.plugins.views.sqlConsole.language;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.swing.text.BadLocationException;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.parser.AbstractParser;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParseResult;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParserNotice;
import org.fife.ui.rsyntaxtextarea.parser.ParseResult;
import org.fife.ui.rsyntaxtextarea.parser.ParserNotice;
import org.gdms.sql.engine.ANTLRCaseInsensitiveInputStream;
import org.gdms.sql.engine.parsing.GdmSQLLexer;
import org.gdms.sql.engine.parsing.GdmSQLParser;

/**
 * A parser for Gdms SQL syntax that provides error locations.
 * @author Antoine Gourlay
 */
class SQLParser extends AbstractParser {

        private RSyntaxTextArea textArea;
        private SQLMetadataManager metManager;

        SQLParser(RSyntaxTextArea textArea, SQLMetadataManager metManager) {
                this.textArea = textArea;
                this.metManager = metManager;
        }

        @Override
        public ParseResult parse(RSyntaxDocument doc, String style) {
                metManager.checkSourcesToLoad();
                String content = "";
                try {
                        content = doc.getText(0, doc.getLength()).trim();

                        DefaultParseResult res = new DefaultParseResult(this);
                        if (content.isEmpty()) {
                                return res;
                        }

                        long start = System.currentTimeMillis();

                        String[] statements = content.split(";");
                        int totalLength = 0;
                        for (int i = 0; i < statements.length; i++) {
                                if (!statements[i].trim().isEmpty()) {
                                        statements[i] += ";";
                                        ParserNotice p = getError(statements[i], totalLength);
                                        totalLength += statements[i].length();
                                        if (p != null) {
                                                res.addNotice(p);
                                        }
                                }
                        }

                        long time = System.currentTimeMillis() - start;
                        res.setParseTime(time);

                        return res;
                } catch (BadLocationException ex) {
                        throw new IllegalStateException(ex);
                }
        }

        private ParserNotice getError(String content, int currPosition) throws BadLocationException {
                if (content.isEmpty()) {
                        return null;
                }

                // getting the engine and parsing the sql statement
                ANTLRInputStream input = null;
                try {
                        input = new ANTLRCaseInsensitiveInputStream(new ByteArrayInputStream(content.getBytes()));
                } catch (IOException ex) {
                        // never happens
                        throw new IllegalStateException(ex);
                }
                GdmSQLLexer lexer = new GdmSQLLexer(input);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                GdmSQLParser parser = new GdmSQLParser(tokens);

                try {
                        parser.start_rule();
                } catch (RecognitionException e) {
                        int length = content.substring(e.charPositionInLine).indexOf(';');
                        if (length == -1) {
                        }
                        int[] location = getErrorLocationAndLength(e, currPosition);
                        DefaultParserNotice not = new DefaultParserNotice(this, getErrorLocationText(location[0], location[1] + location[3]) + " "
                                + getErrorString(e.token, e.getUnexpectedType(), -1), location[0], location[2], location[3]);
                        return not;
                }

                return null;
        }

        private int[] getErrorLocationAndLength(RecognitionException ex, int currentOffset) throws BadLocationException {
                int[] loc = new int[4];
                // 0: line number
                // 1: char in line
                // 2: char in the whole textArea
                // 3: length of the highlighted part

                loc[0] = textArea.getLineOfOffset(currentOffset);

                // line within the String
                if (ex.line > 1) {
                        loc[0] += ex.line - 1;
                        if (ex.charPositionInLine != -1) {
                                loc[1] = ex.charPositionInLine;
                        } else {
                                loc[1] = 0;
                        }
                        loc[1] += 1;
                        loc[2] = textArea.getLineStartOffset(loc[0]) + loc[1];
                        if (ex.token != null && ex.token.getType() != -1) {
                                loc[3] = ex.token.getText().length();
                        } else {
                                loc[3] = textArea.getLineEndOffset(loc[0]) - textArea.getLineStartOffset(loc[0]) - loc[1];
                        }
                } else if (ex.line == 1) {
                        int realPos = currentOffset - textArea.getLineStartOffset(loc[0]);
                        if (ex.charPositionInLine != -1) {
                                loc[1] = realPos + ex.charPositionInLine;
                        } else {
                                loc[1] = realPos;
                        }
                        loc[2] = textArea.getLineStartOffset(loc[0]) + loc[1];
                        if (ex.token != null && ex.token.getType() != -1) {
                                loc[3] = ex.token.getText().length();
                        } else {
                                loc[3] = textArea.getLineEndOffset(loc[0]) - textArea.getLineStartOffset(loc[0]) - loc[1];
                        }
                } else {
                        loc[1] = 0;
                        loc[2] = textArea.getLineStartOffset(loc[0]) + loc[1];
                        loc[3] = textArea.getLineEndOffset(loc[0]) - textArea.getLineStartOffset(loc[0]);
                }

                if (loc[3] == 0 && loc[1] != 0) {
                        loc[3] = 1;
                        loc[2]--;
                        loc[1]--;
                }

                return loc;
        }

        private String getErrorLocationText(int line, int pos) {
                StringBuilder b = new StringBuilder();
                b.append(line);
                b.append(':');
                b.append(pos);
                return b.toString();
        }

        private String getErrorString(Token token, int type, int expecting) {
                StringBuilder b = new StringBuilder();
                if (type == -1) {
                        b.append("unexpected end of query");
                } else if (type == GdmSQLParser.LONG_ID) {
                        b.append("found identifer '").append(token.getText()).append('\'');
                } else {
                        b.append("found ").append(displayTokenName(type));

                }
                if (expecting != -1) {
                        b.append(", expected ").append(displayTokenName(expecting));

                } else if (type != -1) {
                        b.append(", unexpected here");
                }
                b.append('.');
                return b.toString();
        }

        private String displayTokenName(int token) {
                String str = GdmSQLParser.tokenNames[token];
                str = str.replace("T_", "");
                if (str.equalsIgnoreCase("semi")) {
                        str = "';'";
                } else if (str.equalsIgnoreCase("comma")) {
                        str = "','";
                } else if (str.equalsIgnoreCase("lparen")) {
                        str = "'('";
                } else if (str.equalsIgnoreCase("rparen")) {
                        str = "')'";
                } else if (str.equalsIgnoreCase("eq")) {
                        str = "'='";
                }
                return str;
        }
}
