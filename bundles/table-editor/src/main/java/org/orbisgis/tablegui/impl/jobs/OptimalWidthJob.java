/**
 * OrbisGIS is a java GIS application dedicated to research in GIScience.
 * OrbisGIS is developed by the GIS group of the DECIDE team of the 
 * Lab-STICC CNRS laboratory, see <http://www.lab-sticc.fr/>.
 *
 * The GIS group of the DECIDE team is located at :
 *
 * Laboratoire Lab-STICC – CNRS UMR 6285
 * Equipe DECIDE
 * UNIVERSITÉ DE BRETAGNE-SUD
 * Institut Universitaire de Technologie de Vannes
 * 8, Rue Montaigne - BP 561 56017 Vannes Cedex
 * 
 * OrbisGIS is distributed under GPL 3 license.
 *
 * Copyright (C) 2007-2014 CNRS (IRSTV FR CNRS 2488)
 * Copyright (C) 2015-2017 CNRS (Lab-STICC UMR CNRS 6285)
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
 * or contact directly:
 * info_at_ orbisgis.org
 */
package org.orbisgis.tablegui.impl.jobs;

import java.awt.Component;
import java.util.concurrent.ExecutionException;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import org.orbisgis.commons.progress.ProgressMonitor;
import org.orbisgis.commons.progress.SwingWorkerPM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xnap.commons.i18n.I18n;
import org.xnap.commons.i18n.I18nFactory;

/**
 * Computation of the optimal column width of the entire table
 */
public class OptimalWidthJob extends SwingWorkerPM<Integer, Integer> {
        protected final static I18n I18N = I18nFactory.getI18n(OptimalWidthJob.class);
        private final static Logger LOGGER = LoggerFactory.getLogger(OptimalWidthJob.class);
        private JTable table;
        private int selectedColumn;


        public OptimalWidthJob(JTable table, int selectedColumn) {
                this.table = table;
                this.selectedColumn = selectedColumn;
                setTaskName(I18N.tr("Computation of the optimal column width"));
        }

        @Override
        protected Integer doInBackground() throws Exception {
            return getColumnOptimalWidth(table,table.getRowCount(), Integer.MAX_VALUE,
                    selectedColumn, this.getProgressMonitor());
        }

        @Override
        protected void done() {
            try {
                TableColumn col = table.getColumnModel().getColumn(selectedColumn);
                col.setPreferredWidth(get());
            } catch (ExecutionException|InterruptedException ex) {
                LOGGER.error(ex.getLocalizedMessage(), ex);
            }
        }

        /**
         * Compute the optimal width of a table column
         * @param table 
         * @param rowsToCheck Number of rows used to evaluate the optimal width
         * @param maxWidth Limitation of the width
         * @param column Column index
         * @param pm Progress information
         * @return 
         */
        public static int getColumnOptimalWidth(JTable table, int rowsToCheck, int maxWidth,
                int column, ProgressMonitor pm) {
                TableColumn col = table.getColumnModel().getColumn(column);
                int margin = 5;
                int headerMargin = 10;

                // Get width of column header
                TableCellRenderer renderer = col.getHeaderRenderer();

                if (renderer == null) {
                        renderer = table.getTableHeader().getDefaultRenderer();
                }

                Component comp = renderer.getTableCellRendererComponent(table, col.getHeaderValue(), false, false, 0, 0);

                int width = comp.getPreferredSize().width;

                // Check header
                comp = renderer.getTableCellRendererComponent(table, col.getHeaderValue(), false, false, 0, column);
                width = Math.max(width, comp.getPreferredSize().width + 2
                        * headerMargin);
                // Get maximum width of column data
                for (int r = 0; r < rowsToCheck; r++) {
                        if (r / 100 == r / 100.0) {
                                if (pm.isCancelled()) {
                                        break;
                                } else {
                                        pm.progressTo(100 * r / rowsToCheck);
                                }
                        }
                        renderer = table.getCellRenderer(r, column);
                        comp = renderer.getTableCellRendererComponent(table, table.getValueAt(r, column), false, false, r, column);
                        width = Math.max(width, comp.getPreferredSize().width);
                }

                // limit
                width = Math.min(width, maxWidth);

                // Add margin
                width += 2 * margin;

                return width;
        }

}
