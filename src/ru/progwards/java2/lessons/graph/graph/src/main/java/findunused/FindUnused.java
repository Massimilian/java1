package findunused;

import java.util.ArrayList;
import java.util.List;

public class FindUnused {

    private final List<CObject> roots;
    private final List<CObject> objects;

    public FindUnused(List<CObject> roots, List<CObject> objects) {
        this.roots = roots;
        this.objects = objects;
    }

    /**
     * Static method for find unused CObjects
     * @param roots are the root objects
     * @param objects are all the objects
     * @return objects without links
     */
    public static List<CObject> find(List<CObject> roots, List<CObject> objects) {
        FindUnused fu = new FindUnused(roots, objects);
        return fu.find();
    }

    /**
     * Non static method for find unused CObjects
     * @return objects without links
     */
    public List<CObject> find() {
        List<CObject> objectsInRoots = new ArrayList<>();
        for (CObject root : this.roots) {
            tarian(root, objectsInRoots);
        }
        this.objects.removeAll(objectsInRoots);
        return this.objects;
    }

    /**
     * Recursed method for find the object without link
     * @param cObject is an object for check
     * @param objectsInRoots are all using objects
     */
    private void tarian(CObject cObject, List<CObject> objectsInRoots) {
        if (cObject.mark == 0) {
            objectsInRoots.add(cObject);
            cObject.mark = 1;
            for (int i = 0; i < cObject.references.size(); i++) {
                tarian(cObject.references.get(i), objectsInRoots);
            }
            cObject.mark = 2;
        } else if (cObject.mark == 1) {
            throw new CircleException("Circle exception found.");
        } else if (cObject.mark != 2){
            throw new CircleException("Unknown exception.");
        }
    }

    /**
     * Method for test (preparing objects)
     * @return List of CObjects
     */
    private static List<CObject> prepareObjects() {
        List<CObject> objects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            objects.add(prepareOneCObject());
        }
        CObject nullable = prepareOneCObject();
        nullable.mark = -1;
        objects.add(nullable);
        return objects;
    }

    /**
     * Special method for prepare and return one CObject
     * @return CObject
     */
    private static CObject prepareOneCObject() {
        CObject result = new CObject();
        result.references = new ArrayList<>();
        return result;
    }

    /**
     * Method for test (putting objects dependencies)
     * @param objects is a list of CObjects
     * @return root with dependencies without one (incorrect) CObject
     */
    private static List<CObject> prepareRootsWithoutOne(List<CObject> objects) {
        List<CObject> roots = new ArrayList<>();
        objects.get(0).references.add(objects.get(1));
        objects.get(0).references.add(objects.get(2));
        objects.get(0).references.add(objects.get(3));
        objects.get(3).references.add(objects.get(4));
        objects.get(3).references.add(objects.get(5));
        objects.get(0).references.add(objects.get(6));
        objects.get(6).references.add(objects.get(7));
        objects.get(6).references.add(objects.get(8));
        objects.get(6).references.add(objects.get(9));
        roots.add(objects.get(0));
        return roots;
    }
    /**
     * Method for test (putting objects dependencies)
     * @param objects is a list of CObjects
     * @return root with dependencies with one incorrect CObject
     */
    private static List<CObject> prepareRootsWithCObjectException(List<CObject> objects) {
        List<CObject> roots = new ArrayList<>();
        objects.get(0).references.add(objects.get(1));
        objects.get(0).references.add(objects.get(2));
        objects.get(0).references.add(objects.get(3));
        objects.get(3).references.add(objects.get(4));
        objects.get(3).references.add(objects.get(5));
        objects.get(0).references.add(objects.get(6));
        objects.get(6).references.add(objects.get(7));
        objects.get(6).references.add(objects.get(8));
        objects.get(6).references.add(objects.get(9));
        objects.get(6).references.add(objects.get(10));
        roots.add(objects.get(0));
        return roots;
    }

    /**
     * Method for test (putting objects dependencies)
     * @param objects is a list of CObjects
     * @return root with dependencies with one circle dependence
     */
    private static List<CObject> prepareRootsWithRealCircle(List<CObject> objects) {
        List<CObject> roots = new ArrayList<>();
        objects.get(0).references.add(objects.get(1));
        objects.get(0).references.add(objects.get(2));
        objects.get(0).references.add(objects.get(3));
        objects.get(3).references.add(objects.get(4));
        objects.get(3).references.add(objects.get(5));
        objects.get(0).references.add(objects.get(6));
        objects.get(6).references.add(objects.get(7));
        objects.get(6).references.add(objects.get(8));
        objects.get(6).references.add(objects.get(9));
        objects.get(6).references.add(objects.get(10));
        objects.get(10).references.add(objects.get(2));
        objects.get(2).references.add(objects.get(3));
        objects.get(3).references.add(objects.get(0));
        objects.get(10).mark = 0;
        roots.add(objects.get(0));
        return roots;
    }

    public static void main(String[] args) {
        List<CObject> objects = prepareObjects();
        List<CObject> roots = prepareRootsWithoutOne(objects);
        List<CObject> list = FindUnused.find(roots, objects);
        assert list.size() == 1;
        objects = prepareObjects();
        roots = prepareRootsWithCObjectException(objects);
        try {
            list = FindUnused.find(roots, objects);
        } catch (CircleException e) {
            if (e.getMessage().equals("Unknown exception.")) {
                assert true;
            } else {
                assert false;
            }
        } finally {
            try {
                objects = prepareObjects();
                roots = prepareRootsWithRealCircle(objects);
                list = FindUnused.find(roots, objects);
            } catch (CircleException e) {
                if (e.getMessage().equals("Circle exception found.")) {
                    assert true;
                } else {
                    assert false;
                }
            }
        }
    }
}
