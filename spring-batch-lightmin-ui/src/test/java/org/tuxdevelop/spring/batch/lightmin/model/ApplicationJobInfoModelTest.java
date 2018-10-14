package org.tuxdevelop.spring.batch.lightmin.model;


import org.tuxdevelop.spring.batch.lightmin.test.PojoTestBase;

public class ApplicationJobInfoModelTest extends PojoTestBase {
    @Override
    public void performPojoTest() {
        this.testStructureAndBehavior(JobInfoModel.class);
        this.testEquals(JobInfoModel.class);
    }
}
