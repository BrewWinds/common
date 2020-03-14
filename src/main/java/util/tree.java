package util;

import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class tree<T> {

    static class Node<T>{
        T data;
        String id;
        String parentId;
        List<Node<T>> children;

        public Node(){}
        public Node(Node<T> node){
            this.data = node.data;
            this.id = node.id;
            this.parentId = node.parentId;
            this.children = Lists.newArrayList(node.children);
        }




    }


    List<Node<String>> findChildrend(List<Node<String>> rs, List<String> ids, List<Node<String>> all){
        if(CollectionUtils.isEmpty(rs)){
            rs = Lists.newArrayList();
        }
        List<Node<String>> tmp  = Lists.newArrayList();
        for(String id : ids){
            for(Node node : all){
                if(node.parentId.equals(id)){
                    rs.add(node);
                    tmp.add(node);
                }
            }
        }
        return CollectionUtils.isEmpty(rs) ? rs : findChildrend(rs,
                tmp.stream().map(n->n.id).collect(Collectors.toList()), rs);
    }


    public static void main(String[] args) {
        List<Integer> data = Stream.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10).collect(Collectors.toList());
        Collections.shuffle(data);

        List<Node> tdata = data.stream().map(x->{
            Node n = new Node();
            n.id = String.valueOf(x+1);
            n.parentId = String.valueOf(x);
            n.data = "xx"+(x+1);
            return n;
        }).collect(Collectors.toList());

        Node<String> s = tdata.get(3);

        tree t = new tree();

        List<Node<String>>xcv = t.findChildrend(Lists.newArrayList(), Lists.newArrayList(tdata.get(0).id), tdata);
        xcv.stream().forEach(q->System.out.println(q.id));

    }
}
