package findunused;

import java.util.List;

class CObject {
    public List<CObject> references; // ссылки на другие объекты
    int mark;  // пометка для алгоритма
    // 0 - не используется
    // 1 - посещен
    // 2 - используется
}
