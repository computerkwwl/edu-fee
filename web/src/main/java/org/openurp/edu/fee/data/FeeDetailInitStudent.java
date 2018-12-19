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
package org.openurp.edu.fee.data;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.beangle.commons.collection.CollectUtils;
import org.openurp.edu.base.model.Semester;
import org.openurp.edu.base.model.Student;
import org.openurp.edu.base.model.StudentState;
import org.openurp.edu.fee.model.FeeDefault;

/**
 * @author zhouqi 2018年12月7日
 */
public class FeeDetailInitStudent {

  private Semester semester;

  private Map<Long, Student> studentMap;

  private Map<StudentState, FeeDefault> stateFeeDefaultMap;

  private Map<Student, StudentState> currentStateMap;

  private FeeDetailInitStudent() {
    super();
  }

  public FeeDetailInitStudent(Semester semester, List<Student> students,
      Map<StudentState, FeeDefault> feeDefaultMap, Map<Student, StudentState> currentStateMap) {
    this();
    this.semester = semester;
    studentMap = CollectUtils.newHashMap();
    for (Student student : students) {
      studentMap.put(student.getId(), student);
    }
    this.stateFeeDefaultMap = feeDefaultMap;
    this.currentStateMap = currentStateMap;
  }

  public Semester getSemester() {
    return semester;
  }

  public Map<Long, Student> getStudentMap() {
    return studentMap;
  }

  public List<Student> getStudents(Long... studentIds) {
    if (null == studentIds) {
      return CollectUtils.newArrayList(studentMap.values());
    }
    List<Student> students = CollectUtils.newArrayList();
    for (Long studentId : studentIds) {
      students.add(studentMap.get(studentId));
    }
    return students;
  }

  public FeeDefault getFeeDefault(Student student) {
    return getFeeDefault(getCurrentState(student));
  }

  public FeeDefault getFeeDefault(StudentState studentState) {
    return stateFeeDefaultMap.get(studentState);
  }

  public StudentState getCurrentState(Student student) {
    return currentStateMap.get(student);
  }

  public Map<Student, StudentState> getCurrentStateMap(Long... studentIds) {
    Map<Student, StudentState> currentStateMap = CollectUtils.newHashMap();
    for (Long studentId : studentIds) {
      List<Student> students = getStudents(studentId);
      if (CollectionUtils.isNotEmpty(students)) {
        StudentState studentState = getCurrentState(students.get(0));
        if (null != studentState) {
          currentStateMap.put(students.get(0), studentState);
        }
      }
    }
    return currentStateMap;
  }
}
