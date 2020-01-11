SELECT `studentID`, `lastName`, `classID` FROM `student` WHERE `classID` = 2;
Select studentID, lastName
from student
where classID in (select className 
       from class 
       where className = '4A');