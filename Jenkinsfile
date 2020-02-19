
parallel (
    {
        guard {
            println '1'
        } rescue {
            println '2'
        }
    },
    {
        retry 3, {
            println '3'
        }
    }
)
