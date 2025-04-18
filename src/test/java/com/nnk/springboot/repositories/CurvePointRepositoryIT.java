package com.nnk.springboot.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nnk.springboot.domain.CurvePoint;

public class CurvePointRepositoryIT extends AbstractRepositoryIT<CurvePoint, Integer> {

    @Autowired
    private CurvePointRepository curvePointRepository;

    @Override
    protected CurvePoint createEntity() {
        return new CurvePoint(10, 10d, 30d);
    }

    @Override
    protected JpaRepository<CurvePoint, Integer> getRepository() {
        return curvePointRepository;
    }

    @Override
    protected Integer getId(CurvePoint curvePoint) {
        return curvePoint.getId();
    }

    @Override
    protected void verifyCreate(CurvePoint curvePoint) {
        assertThat(curvePoint.getCurveId()).isEqualTo(10);
        assertThat(curvePoint.getTerm()).isEqualTo(10d);
        assertThat(curvePoint.getValuePoint()).isEqualTo(30d);
    }

    @Override
    protected void updateEntity(CurvePoint curvePoint) {
        curvePoint.setCurveId(20);
        curvePoint.setTerm(20d);
        curvePoint.setValuePoint(40d);
    }

    @Override
    protected void verifyUpdate(CurvePoint curvePoint) {
        assertThat(curvePoint.getCurveId()).isEqualTo(20);
        assertThat(curvePoint.getTerm()).isEqualTo(20d);
        assertThat(curvePoint.getValuePoint()).isEqualTo(40d);
    }
}
