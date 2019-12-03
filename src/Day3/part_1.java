package Day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class part_1 {
    public static Point lineLineIntersection(Point A, Point B, Point C, Point D)
    {
        // Line AB represented as a1x + b1y = c1
        int a1 = B.y - A.y;
        int b1 = A.x - B.x;
        int c1 = a1*(A.x) + b1*(A.y);

        // Line CD represented as a2x + b2y = c2
        int a2 = D.y - C.y;
        int b2 = C.x - D.x;
        int c2 = a2*(C.x)+ b2*(C.y);

        int determinant = a1*b2 - a2*b1;

        if (determinant == 0)
        {
            // The lines are parallel. This is simplified
            // by returning a pair of FLT_MAX
            return new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
        else
        {
            int x = (b2*c1 - b1*c2)/determinant;
            int y = (a1*c2 - a2*c1)/determinant;
            return new Point(x, y);
        }
    }

    /***** Intersection code from https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/ *****/

    // Given three colinear points p, q, r, the function checks if
    // point q lies on line segment 'pr'
    static boolean onSegment(Point p, Point q, Point r)
    {
        if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) &&
                q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y))
            return true;

        return false;
    }

    // To find orientation of ordered triplet (p, q, r).
    // The function returns following values
    // 0 --> p, q and r are colinear
    // 1 --> Clockwise
    // 2 --> Counterclockwise
    static int orientation(Point p, Point q, Point r)
    {
        // See https://www.geeksforgeeks.org/orientation-3-ordered-points/
        // for details of below formula.
        int val = (q.y - p.y) * (r.x - q.x) -
                (q.x - p.x) * (r.y - q.y);

        if (val == 0) return 0; // colinear

        return (val > 0)? 1: 2; // clock or counterclock wise
    }

    // The main function that returns true if line segment 'p1q1'
    // and 'p2q2' intersect.
    public static boolean doIntersect(Point p1, Point q1, Point p2, Point q2)
    {
        // Find the four orientations needed for general and
        // special cases
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        // General case
        if (o1 != o2 && o3 != o4)
            return true;

        // Special Cases
        // p1, q1 and p2 are colinear and p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1)) return true;

        // p1, q1 and q2 are colinear and q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1)) return true;

        // p2, q2 and p1 are colinear and p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2)) return true;

        // p2, q2 and q1 are colinear and q1 lies on segment p2q2
        if (o4 == 0 && onSegment(p2, q1, q2)) return true;

        return false; // Doesn't fall in any of the above cases
    }


    /**
     * Populates the list with points from direction file
     * @param csvDirections list of directions as a csv string
     * @param listToPopulate list which should be populated with values
     */
    public static void populateWithPointsFromFile(String csvDirections, List<Point> listToPopulate) {
        char direction;
        int length;
        Point previousPoint;
        String[] directions = csvDirections.split(",");

        for (String dir : directions) {
            direction = dir.charAt(0);
            length = Integer.parseInt(dir.substring(1));
            previousPoint = listToPopulate.get(listToPopulate.size() - 1);

            if (direction == 'R') { // Right - moves in positive direction on X axis
                listToPopulate.add(new Point(previousPoint.x + length, previousPoint.y));
            }
            else if (direction == 'L') { // Left - moves in negative direction on X axes
                listToPopulate.add(new Point(previousPoint.x - length, previousPoint.y));
            }
            else if (direction == 'U') { // Up - moves in positive direction on Y axis
                listToPopulate.add(new Point(previousPoint.x, previousPoint.y + length));
            }
            else if (direction == 'D') { // Down - moves in negative direction on Y axis
                listToPopulate.add(new Point(previousPoint.x, previousPoint.y - length));
            }
        }
    }

    public static void main(String[] args) {
        String inputPath = ".\\src\\Day3\\input.txt", line;
        List<Point> first = new ArrayList<>(),
                second = new ArrayList<>(),
                interesctions = new ArrayList<>();
        Point intersectionPoint;

        // Both lines start from (0,0)
        first.add(new Point(0, 0));
        second.add(new Point(0, 0));

        // Get file for reading directions
        File file = new File(inputPath);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (sc.hasNextLine()) {
            line = sc.nextLine();
            populateWithPointsFromFile(line, first);
        }

        if (sc.hasNextLine()) {
            line = sc.nextLine();
            populateWithPointsFromFile(line, second);
        }

        // Go through all the points and look for intersections
        for (int i = 0; i < first.size() - 1; i ++) {
            for (int j = 0; j < second.size() - 1; j ++) {
                intersectionPoint = lineLineIntersection(first.get(i), first.get(i+1), second.get(j), second.get(j+1));
                // If there is a line, add to an intersection list
                if (doIntersect(first.get(i), first.get(i+1), second.get(j), second.get(j+1)))
                    interesctions.add(intersectionPoint);
            }
        }

        // Remove the first one, as it is (0,0)
        interesctions.remove(0);

        // Go through intersections and find closes distance to central location
        int distance, minDistance = Integer.MAX_VALUE;
        for (Point intersection : interesctions) {
            distance = Math.abs(intersection.x) + Math.abs(intersection.y);
            if (distance < minDistance)
                minDistance = distance;
        }

        System.out.println("distance: " + minDistance);
    }
}
