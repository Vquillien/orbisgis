/*
 * The GDMS library (Generic Datasources Management System)
 * is a middleware dedicated to the management of various kinds of
 * data-sources such as spatial vectorial data or alphanumeric. Based
 * on the JTS library and conform to the OGC simple feature access
 * specifications, it provides a complete and robust API to manipulate
 * in a SQL way remote DBMS (PostgreSQL, H2...) or flat files (.shp,
 * .csv...). GDMS is produced  by the geomatic team of the IRSTV
 * Institute <http://www.irstv.cnrs.fr/>, CNRS FR 2488:
 *    Erwan BOCHER, scientific researcher,
 *    Thomas LEDUC, scientific researcher,
 *    Fernando GONZALEZ CORTES, computer engineer.
 *
 * Copyright (C) 2007 Erwan BOCHER, Fernando GONZALEZ CORTES, Thomas LEDUC
 *
 * This file is part of GDMS.
 *
 * GDMS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GDMS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GDMS. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult:
 *    <http://orbisgis.cerma.archi.fr/>
 *    <http://sourcesup.cru.fr/projects/orbisgis/>
 *    <http://listes.cru.fr/sympa/info/orbisgis-developers/>
 *    <http://listes.cru.fr/sympa/info/orbisgis-users/>
 *
 * or contact directly:
 *    erwan.bocher _at_ ec-nantes.fr
 *    fergonco _at_ gmail.com
 *    thomas.leduc _at_ cerma.archi.fr
 */
package org.gdms.sql.function;

import org.gdms.data.ExecutionException;
import org.gdms.data.metadata.Metadata;
import org.gdms.data.metadata.MetadataUtilities;
import org.gdms.data.types.Type;
import org.gdms.data.types.TypeFactory;
import org.gdms.data.values.Value;
import org.gdms.driver.DriverException;
import org.gdms.sql.customQuery.CustomQuery;
import org.gdms.sql.strategies.IncompatibleTypesException;

public class FunctionValidator {

	public static void failIfNull(Value... values) throws FunctionException {
		for (Value value : values) {
			if (value.getType() == Type.NULL) {
				throw new FunctionException("Cannot operate in null values");
			}
		}
	}

	public static void failIfBadNumberOfArguments(Function function,
			Type[] argumentsTypes, int i) throws IncompatibleTypesException {
		if (argumentsTypes.length != i) {
			throw new IncompatibleTypesException("The function "
					+ function.getName() + " has a wrong number of arguments: "
					+ i + " expected");
		}
	}

	public static void failIfBadNumberOfArguments(Function function,
			Type[] argumentsTypes, int... numbers)
			throws IncompatibleTypesException {
		for (int j : numbers) {
			if (j == argumentsTypes.length) {
				return;
			}
		}
		throw new IncompatibleTypesException("The function "
				+ function.getName() + " has a wrong number of arguments");
	}

	public static void failIfNumberOfArguments(Function function,
			Type[] argumentsTypes, int i) throws IncompatibleTypesException {
		if (argumentsTypes.length == i) {
			throw new IncompatibleTypesException("The function "
					+ function.getName() + " cannot have " + i + " arguments.");
		}
	}

	public static void failIfNotOfType(Value value, int type)
			throws FunctionException {
		if (type != value.getType()) {
			throw new FunctionException(value.toString() + " is not of type "
					+ type);
		}
	}

	public static void failIfNotNumeric(Function function, Type type)
			throws IncompatibleTypesException {
		if (!TypeFactory.isNumerical(type.getTypeCode())) {
			throw new IncompatibleTypesException("Function "
					+ function.getName()
					+ " only operates with numerical types. "
					+ TypeFactory.getTypeName(type.getTypeCode()) + " found");
		}
	}

	public static void failIfNotOfType(Function function, Type type,
			int typeCode) throws IncompatibleTypesException {
		if (type.getTypeCode() != typeCode) {
			throw new IncompatibleTypesException("Function "
					+ function.getName() + " only operates with "
					+ TypeFactory.getTypeName(typeCode) + " types. "
					+ TypeFactory.getTypeName(type.getTypeCode()) + " found");
		}
	}

	public static void failIfNotOfType(CustomQuery customQuery, Type type,
			int typeCode) {
		if (type.getTypeCode() != typeCode) {
			throw new IncompatibleTypesException("Function "
					+ customQuery.getName() + " only operates with "
					+ TypeFactory.getTypeName(typeCode) + " types. "
					+ TypeFactory.getTypeName(type.getTypeCode()) + " found");
		}
	}

	public static void failIfBadNumberOfArguments(CustomQuery customQuery,
			Type[] argumentsTypes, int i) {
		if (argumentsTypes.length != i) {
			throw new IncompatibleTypesException("The function "
					+ customQuery.getName()
					+ " has a wrong number of arguments: " + i + " expected");
		}
	}

	public static void failIfFieldIsNotOfType(final CustomQuery customQuery,
			final String fieldName, final int fieldIndex,
			final int typeCodeOfField, final Metadata metadata)
			throws ExecutionException {
		if (-1 == fieldIndex) {
			throw new ExecutionException("No fieldname '" + fieldName
					+ "' in your table !");
		}

		try {
			final Type[] fieldTypes = MetadataUtilities.getFieldTypes(metadata);
			if (typeCodeOfField != fieldTypes[fieldIndex].getTypeCode()) {
				throw new IncompatibleTypesException(fieldName
						+ " is not of type "
						+ TypeFactory.getTypeName(typeCodeOfField));
			}
		} catch (DriverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}