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
import javax.batch.runtime.JobInstance;

import io.vertx.core.cli.annotations.Description;
import io.vertx.core.cli.annotations.Name;
import io.vertx.core.cli.annotations.Option;
import io.vertx.core.cli.annotations.Summary;
import io.vertx.ext.shell.command.CommandProcess;

@SuppressWarnings("unused")
@Name("list-job-instances")
@Summary("List job instances")
public final class ListJobInstancesCommand extends CommandBase {
    private String jobName;
    private int start;
    private int count = 10;

    @Description("the name of the job")
    @Option(required = true, longName = "job-name", shortName = "j")
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    @Description("the start position within all matching job instances (0-based)")
    @Option(longName = "start", shortName = "s")
    public void setStart(int start) {
        this.start = start;
    }

    @Description("the number of job instances to return, defaults to 10")
    @Option(longName = "count", shortName = "c")
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void process(final CommandProcess process) {
        try {
            final List<JobInstance> jobInstances = jobOperator.getJobInstances(jobName, start, count);
            process.write(String.format("Job instances for job %s:%n", jobName));

            for (JobInstance jobInstance : jobInstances) {
                process.write(String.format("%s%n", jobInstance.getInstanceId()));
            }
            process.end();
        } catch (Exception e) {
            failed(process, e);
        }
    }
}
