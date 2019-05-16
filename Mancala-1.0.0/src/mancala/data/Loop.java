package mancala.data;

import java.util.Iterator;

public final class Loop<E> implements Iterable<E> {
    private Link<E> current;

    public Loop() {
        current = null;
    }

    public int size() {
        if(isEmpty())
            return 0;

        int size = 0;
        Link<E> start = current;

        do {
            ++size;
            advance();
        }
        while(start != current);

        return size;
    }

    public boolean isEmpty() {
        return current == null;
    }

    public E get() {
        return current.getData();
    }

    public void set(E data) {
        current.setData(data);
    }

    public void advance() {
        current = current.getNext();
    }

    public void advanceTo(E element) {
        if(isEmpty() || current.getData() == element)
            return;

        Link<E> start = current;

        do {
            advance();
        }
        while(current.getData() != element && current != start);
    }

    public void backup() {
        current = current.getPrevious();
    }

    public void add(E element) {
        if(current != null) {
            current = new Link<>(element, current);
        }
        else {
            current = new Link<>(element);
        }
    }

    public E remove() {
        if(isEmpty())
            return null;

        Link<E> next = current.getPrevious();
        E data = current.getData();

        if(current.getNext() != current) {
            current.cutOut();
            current = next;
        }
        else {
            current = null;
        }

        return data;
    }

    public void clear() {
        if(isEmpty())
            return;

        for(Link<E> link = current; link.getNext() != link; link = current) {
            current = link.getPrevious();
            link.cutOut();
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new LoopIterator<>(this);
    }

    private static final class Link<E> {
        private E data;

        private Link<E> next;

        private Link<E> previous;

        public Link(E data) {
            this.data = data;
            this.next = this;
            this.previous = this;
        }

        public Link(E data, Link<E> spotToTake) {
            this.data = data;
            this.next = spotToTake.next;
            this.previous = spotToTake;
            spotToTake.next = this;
        }

        public void cutOut() {
            previous.next = next;
            next.previous = previous;

            next = null;
            previous = null;
        }

        public E getData() {
            return data;
        }

        public void setData(E data) {
            this.data = data;
        }

        public Link<E> getNext() {
            return next;
        }

        public Link<E> getPrevious() {
            return previous;
        }
    }

    private static final class LoopIterator<E> implements Iterator<E> {
        private E start;

        private E next;

        private boolean slurped;

        private Loop<E> loop;

        public LoopIterator(Loop<E> loop) {
            this.loop = loop;
            if(!loop.isEmpty()) {
                start = loop.get();
                next = start;
                slurped = false;
            }
            else {
                start = null;
                next = null;
                slurped = true;
            }
        }

        @Override
        public boolean hasNext() {
            return next != start || !slurped;
        }

        @Override
        public E next() {
            E temp = next;
            loop.advance();
            next = loop.get();
            slurped = true;
            return temp;
        }
    }
}
