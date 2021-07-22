<?php

use Illuminate\Support\Facades\Log;

function traceId()
{
    return request()->attributes->get('TraceId');
}


function log_info($message, array $context = [])
{
    $context = $context ?: [];
    $context['traceId'] = traceId();
    Log::info($message, $context);
}


function log_warn($message, array $context = [])
{

    $context = $context ?: [];
    $context['traceId'] = traceId();
    Log::warning($message, $context);
}


function log_error($message, array $context = [])
{

    $context = $context ?: [];
    $context['traceId'] = traceId();
    Log::error($message, $context);
}