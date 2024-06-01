package org.pocotesteable.deliveryapi.enums

enum class State(val stateName: String) {
    ASSIGNED("ASSIGNED"),
    INPROGRESS("INPROGRESS"),
    DELIVERED("DELIVERED"),
    CANCELED("CANCELED"),
    ;

    companion object {
        fun fromString(str: String): State {
            for (state in entries) {
                if (state.stateName == str) {
                    return state
                }
            }
            throw Exception("Estado no encontrado")
        }
    }
}
