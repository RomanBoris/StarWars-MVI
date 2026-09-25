package com.pobezhkin.starwars_mvi.core.feature

open class DoubleCheckContainer<T : Component, D : Dependencies>(
    val initializer: (D) -> T,
) : Container<T, D> {

    private var component: T? = null

    override fun init(dependencies: D) {
        // Первая проверка — БЕЗ блокировки. Если компонент уже собран
        // (обычный случай, почти все вызовы) — даже не заходим в synchronized.
        // В этом и смысл "double-check": не платить за блокировку каждый раз.
        if (component == null) {
            synchronized(javaClass) {
                // Вторая проверка — УЖЕ внутри блокировки. Пока мы ждали на
                // synchronized, другой поток мог успеть создать компонент
                // первым — тогда пересоздавать его нельзя.
                if (component == null) component = initializer(dependencies)
            }
        }
    }

    override fun provide(): T = checkNotNull(component)

    override fun reset() {
        component = null
    }
}