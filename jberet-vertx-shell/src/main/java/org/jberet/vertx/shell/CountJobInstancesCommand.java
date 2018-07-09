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

import io.vertx.core.cli.annotations.Description;
import io.vertx.core.cli.annotations.Name;
import io.vertx.core.cli.annotations.Option;
import io.vertx.core.cli.annotations.Summary;
import io.vertx.ext.shell.command.CommandProcess;

@SuppressWarnings("unused")
@Name("count-job-instances")
@Summary("Count job instances")
public final class CountJobInstancesCommand extends CommandBase {
    private String jobName;

    @Description("the name of the job whose job instances to count")
    @Option(required = true, longName = "job-name", shortName = "j")
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void process(final CommandProcess process) {
        try {
            final int count = jobOperator.getJobInstanceCount(jobName);
            process.write(String.format("%s job instances for job %s%n", count, jobName));
            process.end();
        } catch (Exception e) {
            failed(process, e);
        }
    }
}
