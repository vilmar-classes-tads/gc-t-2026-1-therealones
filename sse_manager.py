import asyncio
from typing import List, Dict, Any

class SSEManager:
    """
    Gerenciador Singleton para conexões Server-Sent Events (SSE).
    Mantém uma lista de filas (uma para cada cliente) para transmitir eventos.
    NOTA: Esta implementação é para um único processo. Para múltiplos workers (Gunicorn),
    seria necessário um backend de mensagens como Redis Pub/Sub.
    """
    _instance = None

    def __new__(cls):
        if cls._instance is None:
            cls._instance = super(SSEManager, cls).__new__(cls)
            cls._instance.connections: List[asyncio.Queue] = []
        return cls._instance

    async def broadcast(self, event: str, data: Dict[str, Any]):
        """Envia um evento para todos os clientes conectados."""
        for queue in self.connections:
            await queue.put({"event": event, "data": data})

sse_manager = SSEManager()