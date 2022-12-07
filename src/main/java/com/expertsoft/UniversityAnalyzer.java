package com.expertsoft;

import com.expertsoft.model.*;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UniversityAnalyzer {
    /**
     * Should return min subject mark
     *
     * @param students  students stream
     * @param subjectId find min mark for subject with such id
     * @return
     */
    public OptionalInt getMinSubjectMark(Stream<Student> students, int subjectId) {
        return students
                .flatMapToInt(stud -> stud.getSubjectMarks().stream()
                        .filter(s -> s.getSubjectId() == subjectId)
                        .mapToInt(m -> m.getMark()))
                .min();
    }

    /**
     * Should return average mark given by teacher
     *
     * @param students  students stream
     * @param teacherId find average given mark for teacher with such id
     * @return
     */
    public OptionalDouble getAverageTeacherMark(Stream<Student> students, int teacherId) {
        return students
                .flatMapToDouble(student -> student.getSubjectMarks().stream()
                        .filter(mark -> mark.getTeacherId() == teacherId)
                        .mapToDouble(mark -> mark.getMark()))
                .average();
    }

    /**
     * Should return min students age (years). Need to count the number of full years.
     *
     * @param students not empty students stream
     * @return
     */
    public Integer getMinStudentAgeInYears(Stream<Student> students) {
        LocalDate today = LocalDate.now();
        return students
                .mapToInt(student -> today.getMonth().getValue() <= student.getBirthday().getMonth().getValue() &&
                        today.getDayOfMonth() < student.getBirthday().getDayOfMonth() ?
                        today.getYear() - student.getBirthday().getYear() - 1 : today.getYear() - student.getBirthday().getYear()
                ).min().orElse(0);
    }

    /**
     * Should return student with highest average mark.
     * If two or more students have the same average mark, then return any of this students.
     *
     * @param students not empty students stream
     * @return
     */
    public Student getStudentWithHighestAverageMark(Stream<Student> students) {
        return students
                .sorted(Comparator.<Student, Double>comparing(student -> student.getSubjectMarks().stream()
                        .mapToDouble(sm -> sm.getMark())
                        .average()
                        .orElse(0)).reversed())
                .findFirst()
                .orElse(null);
    }

    /**
     * Return sorted students list.
     * If two students have the same count of marks, then students should be ordered by surname
     *
     * @param students students stream
     * @return
     */
    public List<Student> sortStudentsByCountOfMarks(Stream<Student> students) {
        return students
                .sorted(Comparator.<Student, Integer>comparing(student -> student.getSubjectMarks().size()).reversed()
                        .thenComparing(student -> student.getSurname()))
                .collect(Collectors.toList());
    }

    /**
     * Should return IDs of subjects sorted by academic performance in ascending order.
     *
     * @param students students stream
     * @return
     */
    public List<Integer> getSubjectsByAcademicPerformance(Stream<Student> students) {
        return students
                .flatMap(student -> student.getSubjectMarks().stream())
                .collect(Collectors.groupingBy(SubjectMark::getSubjectId, Collectors.averagingDouble(SubjectMark::getMark)))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Should return the subject that most teachers lead.
     * If two or more subject lead the same count of teacher, then return any of this subjects.
     *
     * @param teachers not empty teachers stream
     * @return
     */
    public Subject getSubjectThatMostTeachersLead(Stream<Teacher> teachers) {
        return teachers.flatMap(teacher -> teacher.getTaughtSubjects().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(entry -> entry.getValue()))
                .map(entry -> entry.getKey())
                .orElse(null);
    }

    /**
     * Should sort excellent students by surname.
     * A student is considered an excellent student if his average mark is at least 8.
     * A student is considered a graduate if his age is not less than 21.
     *
     * @param students students stream
     * @return
     */
    public List<Student> getGraduatedExcellentStudents(Stream<Student> students) {
        return null;
    }

    /**
     * Should return the head of the a department whose students have the highest average mark.
     * If students from two or more departments have the same average mark, then return any head of this departments.
     *
     * @param departments not empty departments stream
     * @return
     */
    public Teacher getHeadOfTheMostSuccessfulDepartment(Stream<Department> departments) {
        //TODO
        return null;
    }

    /**
     * Should return subjects list that head teaches in his department.
     *
     * @param department find corresponding subjects for this department
     * @return
     */
    public List<Subject> getSubjectsThatHeadTeachesInHisDepartment(Department department) {
        //TODO
        return null;
    }
}
