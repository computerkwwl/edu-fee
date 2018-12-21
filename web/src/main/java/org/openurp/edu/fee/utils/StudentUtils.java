/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright © 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openurp.edu.fee.utils;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.beangle.commons.collection.CollectUtils;
import org.openurp.edu.base.model.Semester;
import org.openurp.edu.base.model.Student;
import org.openurp.edu.base.model.StudentState;

/**
 * @author zhouqi 2018年12月18日
 */
public class StudentUtils {

  public static StudentState getStudentState(Student student, Long studentStateId) {
    if (null == student || null == studentStateId) {
      return null;
    }
    for (StudentState studentState : student.getStates()) {
      if (studentState.getId().equals(studentStateId)) {
        return studentState;
      }
    }
    return null;
  }

  public static List<StudentState> getStudentStates(Student student, Semester semester) {
    List<StudentState> studentStates = CollectUtils.newArrayList();
    if (!ObjectUtils.allNotNull(student, semester, semester.getId(), semester.getBeginOn())) {
      return studentStates;
    }
    return getStudentStates(student, semester.getBeginOn(), semester.getEndOn());
  }

  public static List<StudentState> getStudentStates(Student student, Date fromAt, Date toAt) {
    List<StudentState> studentStates = CollectUtils.newArrayList();
    if (!ObjectUtils.allNotNull(student, fromAt)) {
      return studentStates;
    }
    for (StudentState studentState : student.getStates()) {
      if ((null == toAt || studentState.getBeginOn().before(toAt)) && studentState.getEndOn().after(fromAt)) {
        studentStates.add(studentState);
      }
    }
    return studentStates;
  }

  public static List<Student> getStudents(Collection<StudentState> states) {
    Set<Student> students = CollectUtils.newHashSet();
    for (StudentState studentState : states) {
      students.add(studentState.getStd());
    }
    return CollectUtils.newArrayList(students);
  }
}
