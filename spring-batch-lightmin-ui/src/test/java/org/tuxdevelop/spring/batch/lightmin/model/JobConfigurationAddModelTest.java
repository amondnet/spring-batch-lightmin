package org.tuxdevelop.spring.batch.lightmin.model;


import org.tuxdevelop.spring.batch.lightmin.test.PojoTestBase;

public class JobConfigurationAddModelTest extends PojoTestBase {
    @Override
    public void performPojoTest() {
        this.testStructureAndBehavior(JobConfigurationAddModel.class);
        this.testEquals(JobConfigurationAddModel.class);
    }
}
