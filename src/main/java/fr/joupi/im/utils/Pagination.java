package fr.joupi.im.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Pagination<T> {

    private final List<Page> pages = new ArrayList<>();
    private final List<T> elements = new ArrayList<>();
    private int elementsPerPage;

    public Pagination(int elementsPerPage) {
        this.setElementsPerPage(elementsPerPage);
    }

    public Pagination(Collection<T> elements, int elementsPerPage) {
        this(elementsPerPage);
        elements.forEach(this::addElement);
    }

    public void addElement(T element) {
        this.elements.add(element);

        if (this.getRequiredPages() > this.countPages()) {
            int index = this.elements.size() - 1;
            Page page = new Page(this.countPages() + 1, index, index + this.elementsPerPage);

            this.pages.add(page);
        }
    }

    public void removeElement(T element) {

        if (!this.elements.contains(element)) return;

        this.elements.remove(element);
        int pages = this.countPages();

        if (pages > 1 && this.getRequiredPages() < pages) this.pages.remove(this.getLast());
    }

    public void removeElement(int index) {

        if (index < 0)
            throw new IllegalArgumentException("Index cannot be negative.");

        if (index >= this.elements.size())
            throw new IllegalArgumentException(String.format("Index : %d Size: %d", index, this.countElements()));

        this.elements.remove(index);

        int pages = this.countPages();

        if (pages > 1 && this.getRequiredPages() < pages) this.pages.remove(this.getLast());
    }

    public boolean containsElement(T element) {
        return this.elements.contains(element);
    }

    public int indexOf(T element) {
        return this.elements.indexOf(element);
    }

    public Page getLast() {
        return this.pages.get(this.pages.size() - 1);
    }

    public Page getFirst() {
        return this.pages.get(0);
    }

    public boolean contains(Page page) {
        return this.pages.contains(page);
    }

    public boolean isLast(Page page) {
        return page.equals(this.getLast());
    }

    public boolean isFirst(Page page) {
        return page.equals(this.getFirst());
    }

    public boolean hasNext(Page page) {

        if (!this.contains(page))
            throw new IllegalArgumentException("Page does not belong to the pagination.");

        return !this.isLast(page);
    }

    public boolean hasPrevious(Page page) {

        if (!this.contains(page))
            throw new IllegalArgumentException("Page does not belong to the pagination.");

        return !this.isFirst(page);
    }

    public Page getNext(Page page) {

        if (!this.contains(page))
            throw new IllegalArgumentException("Page does not belong to the pagination.");

        return this.hasNext(page) ? this.pages.get(this.pages.indexOf(page) + 1) : null;
    }

    public Page getPrevious(Page page) {

        if (!this.contains(page))
            throw new IllegalArgumentException("Page does not belong to the pagination.");

        return this.hasPrevious(page) ? this.pages.get(this.pages.indexOf(page) - 1) : null;
    }

    public Page getPage(int number) {

        if (number < 1 || number > this.pages.size())
            throw new IllegalArgumentException(String.format("Page number must be between 1 and %d (include).", this.pages.size()));

        return this.pages.get(number - 1);
    }

    public boolean hasPage(int number) {
        return number > 0 && number <= this.pages.size();
    }

    public int countPages() {
        return this.pages.size();
    }

    public List<Page> getPages() {
        return new ArrayList<>(this.pages);
    }

    public int getElementsPerPage() {
        return this.elementsPerPage;
    }

    public void setElementsPerPage(int elementsPerPage) {
        this.elementsPerPage = elementsPerPage;
        this.generatePages();
    }

    public int countElements() {
        return this.elements.size();
    }

    private int getRequiredPages() {
        return (int) Math.ceil((double) this.elements.size() / this.elementsPerPage);
    }

    private void generatePages() {

        List<T> elements = new ArrayList<>(this.elements);

        this.pages.clear();
        this.elements.clear();

        elements.forEach(this::addElement);

        if (this.pages.size() == 0) this.pages.add(new Page(1, 0, this.elementsPerPage));
    }

    public List<T> getElements() {
        return Collections.unmodifiableList(this.elements);
    }

    public class Page {

        private final int number, begin, end;

        private Page(int number, int begin, int end) {
            this.number = number;
            this.begin = begin;
            this.end = end;
        }

        public List<T> getElements() {
            List<T> allElements = Pagination.this.elements;
            int toIndex = Math.min(allElements.size(), this.end);

            return allElements.subList(this.begin, toIndex);
        }

        public int countElements() {
            return this.getElements().size();
        }

        public int getNumber() {
            return this.number;
        }

        public boolean belongsToPage(T element) {
            return this.getElements().contains(element);
        }

    }
}