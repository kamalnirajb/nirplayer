package niraj.data.mapper

interface Mapper<T : Any, R : Any> {
    fun toModal(value: T): R
    fun fromModal(value: R): T
}