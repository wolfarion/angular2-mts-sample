package com.cbgr.assemble;

import com.cbgr.dto.Dto;
import com.cbgr.entity.Persistable;

/**
 * Интерфейс ассемблера.
 */
public interface Assembler<D extends Dto, P extends Persistable> {

    D toDto(P model);

    P fromDto(D dto);

}
