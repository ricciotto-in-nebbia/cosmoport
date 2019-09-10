package com.space.repository;

import com.space.model.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShipRepository extends JpaRepository<Ship, Long>, JpaSpecificationExecutor<Ship> {

}

/*
* Далее, принимая во внимание использование базы данных для хранения записей,
* переходим на следующую ступень в разработке приложения: создадим в директории “repository” интерфейс NoteRepository,
* - связующий элемент в цепочке обмена, - и унаследуем наиболее подходящий для дальнейшей работы репозиторий
* с указанием хранимой сущности и целочисленного итератора для обращения.
*
* public interface NoteRepository extends JpaRepository<Note, Integer> {
}
*
* */