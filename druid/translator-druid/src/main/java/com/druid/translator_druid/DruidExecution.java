/*
 * JBoss, Home of Professional Open Source.
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */

package com.druid.translator_druid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.teiid.language.Select;
import org.teiid.logging.LogConstants;
import org.teiid.logging.LogManager;
import org.teiid.translator.DataNotAvailableException;
import org.teiid.translator.ResultSetExecution;
import org.teiid.translator.TranslatorException;

import com.yahoo.sql4d.sql4ddriver.DDataSource;

/**
 * Represents the execution of a command.
 */
public class DruidExecution implements ResultSetExecution {

	private Select command;

	// Execution state
	private List<List<Object>> results;
	int[] neededColumns;
	private Select query;
	private int index = 0;
	private DruidConnection connection;
	private DDataSource dDataSource;

	/**
     * 
     */
	public DruidExecution(Select query, DruidConnection connection) {
		this.query = query;
		this.connection = connection;
		this.dDataSource = connection.getConnection();
	}

	public void execute() throws TranslatorException {
		// Log our command
		LogManager.logDetail(LogConstants.CTX_CONNECTOR, DruidPlugin.UTIL
				.getString("execute_query", new Object[] { "druid", command })); //$NON-NLS-1$

		try {
			results = dDataSource.query(query.toString()).right().get().right()
					.get().baseAllRows;
		} catch (Exception ex) {
			throw new TranslatorException(ex);
		}

	}

	// "While the command is being executed, the translator provides results via
	// the ResultSetExecution's `next` method. The `next` method should return
	// null to indicate the end of results."
	public List<?> next() throws TranslatorException, DataNotAvailableException {
		if (results == null) {
			throw new DataNotAvailableException();
		} else if (index < results.size()) {
			
			return results.get(index++);
		} else {
			
			return null;
		}
	}

	/**
	 * @param row
	 * @param neededColumns
	 */
	static List<Object> projectRow(List<?> row, int[] neededColumns) {
		List<Object> output = new ArrayList<Object>(neededColumns.length);

		for (int i = 0; i < neededColumns.length; i++) {
			output.add(row.get(neededColumns[i] - 1));
		}

		return output;
	}

	public void close() {
		LogManager.logDetail(LogConstants.CTX_CONNECTOR,
				DruidPlugin.UTIL.getString("close_query")); //$NON-NLS-1$

	}

	public void cancel() throws TranslatorException {
		LogManager.logDetail(LogConstants.CTX_CONNECTOR,
				DruidPlugin.UTIL.getString("cancel_query")); //$NON-NLS-1$

	}

}
