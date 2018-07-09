/*
 * Copyright (c) 2017 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.jberet.vertx.shell;

import java.util.Properties;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;

import io.vertx.ext.shell.command.AnnotatedCommand;
import io.vertx.ext.shell.command.CommandProcess;
import org.jberet.util.BatchUtil;

abstract public class CommandBase extends AnnotatedCommand {
    protected static final JobOperator jobOperator = BatchRuntime.getJobOperator();
    public static final int EXIT_CODE_FAILED = 1;

    protected static void failed(CommandProcess process, Exception e) {
        process.write(e.toString()).write(BatchUtil.NL);
        process.end(EXIT_CODE_FAILED);
    }

    protected static Properties parseJobParameters(String line) {
        Properties props = new Properties();
        final String[] pairs = line.split(",");
        for (String e : pairs) {
            final int equalSignPos = e.indexOf('=');
            if (equalSignPos < 1) {
                throw new IllegalArgumentException(line);
            }
            props.setProperty(e.substring(0, equalSignPos), e.substring(equalSignPos + 1));
        }
        return props;
    }
}
