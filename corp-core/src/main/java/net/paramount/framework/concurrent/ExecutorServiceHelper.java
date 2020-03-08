/**
 * 
 */
package net.paramount.framework.concurrent;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import net.paramount.common.ListUtility;
import net.paramount.framework.component.CompCore;
import net.paramount.framework.model.ExecutionContext;
import net.paramount.global.GlobalConstants;

/**
 * @author ducbq
 *
 */
@Component
public class ExecutorServiceHelper extends CompCore {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6717449694098903669L;

  @Inject
  private ApplicationContext appContext;

	private WorkerThreadBase invokeWorkerThread(ExecutionContext executionContext) {
		Class<?> beanClass = (Class<?>)executionContext.get(GlobalConstants.KEY_CONTEXT_CLASS);
		return (WorkerThreadBase)appContext.getBean(beanClass, executionContext);
	}

	private ExecutorService getExecutorService() {
		return Executors.newFixedThreadPool(GlobalConstants.defaultNumberOfThreads);
	}

	public Future<ExecutionContext> startThread(ExecutionContext executionContext) throws InterruptedException{
		WorkerThreadBase workerThread = invokeWorkerThread(executionContext);
		ExecutorService executorService = getExecutorService();

		List<Future<ExecutionContext>> futures = executorService.invokeAll(ListUtility.createDataList(workerThread));
		return futures.get(0);//CompletableFuture.completedFuture(executionContext);
	}
}
