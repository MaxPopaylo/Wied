package ua.wied.domain.models.exceptions

sealed class NetworkException : Exception() {
    data object NoConnectionException : NetworkException() {
        private fun readResolve(): Any = NoConnectionException
    }

    data object BadRequestException : NetworkException() {
        private fun readResolve(): Any = BadRequestException
    }

    data object UnauthorizedException : NetworkException() {
        private fun readResolve(): Any = UnauthorizedException
    }

    data object PageNotFoundException : NetworkException() {
        private fun readResolve(): Any = PageNotFoundException
    }

    data object UnknownErrorException : NetworkException() {
        private fun readResolve(): Any = UnknownErrorException
    }
}