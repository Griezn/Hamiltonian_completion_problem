import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnionFindTests {

    @Test
    public void testMakeSet()
    {
        UnionFind<Integer> unionFind = new UnionFind<>();
        unionFind.makeSet(0);
        unionFind.makeSet(1);
        unionFind.makeSet(2);
        unionFind.makeSet(3);
        unionFind.makeSet(4);
        unionFind.makeSet(5);
        unionFind.makeSet(6);
        unionFind.makeSet(7);
        unionFind.makeSet(8);
        unionFind.makeSet(9);

        assertEquals(0, unionFind.find(0));
        assertEquals(1, unionFind.find(1));
        assertEquals(2, unionFind.find(2));
        assertEquals(3, unionFind.find(3));
        assertEquals(4, unionFind.find(4));
        assertEquals(5, unionFind.find(5));
        assertEquals(6, unionFind.find(6));
        assertEquals(7, unionFind.find(7));
        assertEquals(8, unionFind.find(8));
        assertEquals(9, unionFind.find(9));
    }


    @Test
    public void testUnion()
    {
        UnionFind<Integer> unionFind = getUnionFind();

        assertEquals(0, unionFind.find(0));
        assertEquals(0, unionFind.find(1));
        assertEquals(0, unionFind.find(2));
        assertEquals(0, unionFind.find(3));
        assertEquals(0, unionFind.find(4));
        assertEquals(0, unionFind.find(5));
        assertEquals(0, unionFind.find(6));
        assertEquals(0, unionFind.find(7));
        assertEquals(0, unionFind.find(8));
        assertEquals(0, unionFind.find(9));

        assertEquals(0, unionFind.getParent(0));
        assertEquals(0, unionFind.getParent(1));
        assertEquals(0, unionFind.getParent(2));
        assertEquals(0, unionFind.getParent(3));
        assertEquals(0, unionFind.getParent(4));
        assertEquals(0, unionFind.getParent(5));
        assertEquals(0, unionFind.getParent(6));
        assertEquals(0, unionFind.getParent(7));
        assertEquals(0, unionFind.getParent(8));
        assertEquals(0, unionFind.getParent(9));
    }


    private static UnionFind<Integer> getUnionFind()
    {
        UnionFind<Integer> unionFind = new UnionFind<>();
        unionFind.makeSet(0);
        unionFind.makeSet(1);
        unionFind.makeSet(2);
        unionFind.makeSet(3);
        unionFind.makeSet(4);
        unionFind.makeSet(5);
        unionFind.makeSet(6);
        unionFind.makeSet(7);
        unionFind.makeSet(8);
        unionFind.makeSet(9);

        unionFind.union(0, 1);
        unionFind.union(2, 3);
        unionFind.union(4, 5);
        unionFind.union(6, 7);
        unionFind.union(8, 9);
        unionFind.union(0, 2);
        unionFind.union(4, 6);
        unionFind.union(0, 4);
        unionFind.union(0, 8);

        return unionFind;
    }
}
