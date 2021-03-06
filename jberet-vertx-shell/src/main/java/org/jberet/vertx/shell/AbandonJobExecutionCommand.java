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

import io.vertx.core.cli.annotations.Argument;
import io.vertx.core.cli.annotations.Description;
import io.vertx.core.cli.annotations.Name;
import io.vertx.core.cli.annotations.Summary;
import io.vertx.ext.shell.command.CommandProcess;

@SuppressWarnings("unused")
@Name("abandon-job-execution")
@Summary("Abandon a job execution")
public final class AbandonJobExecutionCommand extends CommandBase {
    private long jobExecutionId;

    @Description("the job execution id to abandon")
    @Argument(index = 0, argName = "jobExecutionId")
    public void setJobExecutionId(long i) {
        this.jobExecutionId = i;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void process(final CommandProcess process) {
        try {
            jobOperator.abandon(jobExecutionId);
            process.write(String.format("Abandoned job execution: %s%n", jobExecutionId));
            process.end();
        } catch (Exception e) {
            failed(process, e);
        }
    }
}
