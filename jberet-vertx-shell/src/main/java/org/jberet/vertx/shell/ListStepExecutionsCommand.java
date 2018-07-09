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

import java.util.List;
import javax.batch.runtime.StepExecution;

import io.vertx.core.cli.annotations.Description;
import io.vertx.core.cli.annotations.Name;
import io.vertx.core.cli.annotations.Option;
import io.vertx.core.cli.annotations.Summary;
import io.vertx.ext.shell.command.CommandProcess;

import static org.jberet.vertx.shell.GetStepExecutionCommand.format;

@SuppressWarnings("unused")
@Name("list-step-executions")
@Summary("List step executions belonging to a job execution")
public final class ListStepExecutionsCommand extends CommandBase {
    private long jobExecutionId;

    @Description("the job execution id whose step executions to list")
    @Option(required = true, longName = "job-execution-id", shortName = "j")
    public void setJobExecutionId(long i) {
        this.jobExecutionId = i;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void process(final CommandProcess process) {
        try {
            final List<StepExecution> stepExecutions = jobOperator.getStepExecutions(jobExecutionId);
            process.write(String.format("Step executions in job execution %s:%n", jobExecutionId));
            for (StepExecution stepExecution : stepExecutions) {
                process.write(format(stepExecution.getStepExecutionId(), stepExecution.getBatchStatus()));
            }
            process.end();
        } catch (Exception e) {
            failed(process, e);
        }
    }
}
