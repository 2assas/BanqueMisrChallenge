package banquemisr.challenge05.domain.repositories

interface Mapper<Dto, Domain> {
    fun toDomain(dto: Dto): Domain
}
