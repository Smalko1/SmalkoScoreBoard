package com.smalko.scoreboard.interceptor;

/*@RequiredArgsConstructor
public class TransactionInterceptor {

    private final SessionFactory sessionFactory;

    @RuntimeType
    public Object intercept(@SuperCall Callable<Object> callable, @Origin Method method) throws Exception {
        Transaction transaction = null;
        boolean transactionStarter = false;
        if (method.isAnnotationPresent(Transactional.class)){
            transaction = sessionFactory.getCurrentSession().getTransaction();
            if (!transaction.isActive()){
                transaction.begin();
                transactionStarter = true;
            }
        }
        Object result;
        try {
            result = callable.call();
            if (transactionStarter){
                transaction.commit();
            }
        } catch (Exception e) {
            if (transactionStarter) {
                transaction.rollback();
            }
            throw e;
        }
        return result;
    }
}
 */
