package org.tuxdevelop.spring.batch.lightmin.model;


import org.tuxdevelop.spring.batch.lightmin.test.PojoTestBase;

public class JobInstanceModelTest extends PojoTestBase {
    @Override
    public void performPojoTest() {
        this.testStructureAndBehavior(JobInstanceModel.class);
        this.testEquals(JobInstanceModel.class);
    }
}
