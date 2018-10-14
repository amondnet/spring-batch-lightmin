package org.tuxdevelop.spring.batch.lightmin.model;


import org.tuxdevelop.spring.batch.lightmin.test.PojoTestBase;

public class JobLauncherModelTest extends PojoTestBase {
    @Override
    public void performPojoTest() {
        this.testStructureAndBehavior(JobLauncherModel.class);
        this.testEquals(JobLauncherModel.class);
    }
}
