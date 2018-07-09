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

import javax.batch.runtime.JobExecution;

import io.vertx.core.cli.annotations.Argument;
import io.vertx.core.cli.annotations.Description;
import io.vertx.core.cli.annotations.Name;
import io.vertx.core.cli.annotations.Summary;
import io.vertx.ext.shell.command.CommandProcess;

import static org.jberet.vertx.shell.GetStepExecutionCommand.format;

@SuppressWarnings("unused")
@Name("get-job-execution")
@Summary("Get job execution details")
public final class GetJobExecutionCommand extends CommandBase {
    private long jobExecutionId;

    @Description("the job execution id")
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
            final JobExecution je = jobOperator.getJobExecution(jobExecutionId);
            process.write(format("execution id", jobExecutionId))
                    .write(format("job name", je.getJobName()))
                    .write(format("batch status", je.getBatchStatus()))
                    .write(format("exit status", je.getExitStatus()))
                    .write(format("create time", je.getCreateTime()))
                    .write(format("start time", je.getStartTime()))
                    .write(format("update time", je.getLastUpdatedTime()))
                    .write(format("end time", je.getEndTime()))
                    .write(format("job params", je.getJobParameters()));
            process.end();
        } catch (Exception e) {
            failed(process, e);
        }
    }
}
