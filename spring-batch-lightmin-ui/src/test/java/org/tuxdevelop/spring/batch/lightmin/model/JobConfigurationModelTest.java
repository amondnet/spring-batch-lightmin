package org.tuxdevelop.spring.batch.lightmin.model;


import org.tuxdevelop.spring.batch.lightmin.test.PojoTestBase;

public class JobConfigurationModelTest extends PojoTestBase {
    @Override
    public void performPojoTest() {
        this.testStructureAndBehavior(JobConfigurationModel.class);
        this.testEquals(JobConfigurationModel.class);
    }
}
