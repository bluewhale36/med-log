package com.bluewhale.medlog.common.application.usecase;

public interface IF_UseCase<I, O> {

    O execute(I input);
}
